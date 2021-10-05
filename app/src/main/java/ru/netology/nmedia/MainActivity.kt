package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.activity.viewModels
import ru.netology.nmedia.adapter.OnActionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

import ru.netology.nmedia.dto.longToString
import ru.netology.nmedia.utils.AndroidUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter (
            //likeListener = {viewModel.likeById(it.id)},
            //shareListener = {viewModel.shareById(it.id)},
            //removeListener = {viewModel.removeById(it.id)}
            object : OnActionListener {
                override fun onEditClicked(post: Post) {
                    viewModel.edit(post)
                }

                override fun onRemoveClicked(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onLikeClicked(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShareClicked(post: Post) {
                    viewModel.shareById(post.id)
                }
            }
        )
        binding.posts.adapter = adapter
        viewModel.data.observe(this) { posts -> adapter.posts = posts }
        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                return@observe
            }
            binding.content.setText(it.content)
            binding.content.requestFocus()
        }
        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(context, "Content must not be empty!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
    }
}
