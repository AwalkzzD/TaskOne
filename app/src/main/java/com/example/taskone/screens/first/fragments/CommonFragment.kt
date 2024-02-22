package com.example.taskone.screens.first.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.R
import com.example.taskone.data.local.AppDatabase
import com.example.taskone.data.models.ModelReferenceClass
import com.example.taskone.data.models.posts.Post
import com.example.taskone.data.models.products.Product
import com.example.taskone.data.models.quotes.Quote
import com.example.taskone.data.models.todos.Todo
import com.example.taskone.databinding.FragmentCommonBinding
import com.example.taskone.screens.adapter.GenericDataAdapter
import com.example.taskone.screens.first.viewmodel.PostsViewModel
import com.example.taskone.screens.first.viewmodel.ProductsViewModel
import com.example.taskone.screens.first.viewmodel.QuotesViewModel
import com.example.taskone.screens.first.viewmodel.TodosViewModel
import com.squareup.picasso.Picasso

class CommonFragment : Fragment() {

    private lateinit var binding: FragmentCommonBinding
    private lateinit var genericDataAdapter: GenericDataAdapter<ModelReferenceClass>
    private lateinit var postViewModel: PostsViewModel
    private lateinit var todosViewModel: TodosViewModel
    private lateinit var quotesViewModel: QuotesViewModel
    private lateinit var productsViewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommonBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        @LayoutRes var layoutRes: Int? = null
        super.onViewCreated(view, savedInstanceState)

        when (arguments?.getString("instance")) {
            "Todos" -> {
                layoutRes = R.layout.todo_list_item
                initTodosViewModel()
            }

            "Quotes" -> {
                layoutRes = R.layout.quote_list_item
                initQuotesViewModel()
            }

            "Products" -> {
                layoutRes = R.layout.product_list_item
                initProductsViewModel()
            }

            "Posts" -> {
                layoutRes = R.layout.post_list_item
                initPostViewModel()
            }
        }
        initRecyclerView(layoutRes)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun initRecyclerView(layoutRes: Int?) {
        genericDataAdapter =
            GenericDataAdapter<ModelReferenceClass>(
                requireActivity(),
                layoutRes!!
            ) { item, itemView ->
                when (item) {
                    is Post -> {
                        val postID: TextView = itemView.findViewById(R.id.postID)
                        val postTitle: TextView = itemView.findViewById(R.id.postTitle)
                        val postReactions: TextView = itemView.findViewById(R.id.postReactions)

                        with(item) {
                            postID.text = this.id.toString()
                            postTitle.text = this.title
                            postReactions.text = "${this.reactions} Likes"
                        }
                    }

                    is Product -> {
                        val productThumbnail: ImageView =
                            itemView.findViewById(R.id.productThumbnail)
                        val productName: TextView = itemView.findViewById(R.id.productName)
                        val productPrice: TextView = itemView.findViewById(R.id.productPrice)

                        with(item) {
                            Picasso.get().load(this.thumbnail.toUri()).into(productThumbnail)
                            productName.text = this.title
                            productPrice.text = "$${this.price}"
                        }
                    }

                    is Quote -> {
                        val quoteID: TextView = itemView.findViewById(R.id.quoteID)
                        val quoteDetail: TextView = itemView.findViewById(R.id.quoteDetail)

                        with(item) {
                            quoteID.text = this.id.toString()
                            quoteDetail.text = this.quote
                        }
                    }

                    is Todo -> {
                        val todoID: TextView = itemView.findViewById(R.id.todoID)
                        val todoDetail: TextView = itemView.findViewById(R.id.todoDetail)
                        val todoStatus: TextView = itemView.findViewById(R.id.todoStatus)

                        with(item) {
                            todoID.text = this.id.toString()
                            todoDetail.text = this.todo
                            todoStatus.text = this.completed.toString()
                        }
                    }
                }


            }
        binding.recyclerView.adapter = genericDataAdapter

        binding.addToRoomDB.setOnClickListener {
            saveToDB()
        }
    }

    private fun saveToDB() {
        val dbInstance = AppDatabase.getInstance(requireContext())
        when (arguments?.getString("instance")) {
            "Todos" -> dbInstance!!.todosDao().insert(genericDataAdapter.getData() as List<Todo>)

            "Quotes" -> dbInstance!!.quotesDao().insert(genericDataAdapter.getData() as List<Quote>)

            "Products" -> dbInstance!!.productsDao().insert(genericDataAdapter.getData() as List<Product>)

            "Posts" -> dbInstance!!.postsDao().insert(genericDataAdapter.getData() as List<Post>)
        }
        Toast.makeText(
            requireActivity(),
            "${genericDataAdapter.itemCount} rows added",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initTodosViewModel() {
        todosViewModel = ViewModelProvider(this)[TodosViewModel::class.java]
        todosViewModel.getLiveData().observe(viewLifecycleOwner) {
            genericDataAdapter.addData(it!!)
            genericDataAdapter.notifyDataSetChanged()
        }
        todosViewModel.getTodos()

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && !binding.recyclerView.canScrollVertically(1)) {
                    todosViewModel.getTodos()
                }
            }
        })
    }

    private fun initQuotesViewModel() {
        quotesViewModel = ViewModelProvider(this)[QuotesViewModel::class.java]
        quotesViewModel.getLiveData().observe(viewLifecycleOwner) {
            genericDataAdapter.addData(it!!)
            genericDataAdapter.notifyDataSetChanged()
        }
        quotesViewModel.getQuotes()

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && !binding.recyclerView.canScrollVertically(1)) {
                    quotesViewModel.getQuotes()
                }
            }
        })
    }

    private fun initPostViewModel() {
        postViewModel = ViewModelProvider(this)[PostsViewModel::class.java]
        postViewModel.getLiveData().observe(viewLifecycleOwner) {
            genericDataAdapter.addData(it!!)
            genericDataAdapter.notifyDataSetChanged()
        }
        postViewModel.getPosts()

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && !binding.recyclerView.canScrollVertically(1)) {
                    postViewModel.getPosts()
                }
            }
        })
    }

    private fun initProductsViewModel() {
        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        productsViewModel.getLiveData().observe(viewLifecycleOwner) {
            genericDataAdapter.addData(it!!)
            genericDataAdapter.notifyDataSetChanged()
        }
        productsViewModel.getProducts()

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && !binding.recyclerView.canScrollVertically(1)) {
                    productsViewModel.getProducts()
                }
            }
        })
    }
}