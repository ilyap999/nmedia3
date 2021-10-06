package ru.netology.nmedia.repository

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory : PostRepository {

    private var postId = 1L

        private val defaultPosts = listOf(
            Post(
                id = postId++,
                content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                published = "21 мая в 18:36",
                author = "Нетология. Университет интернет-профессий будущего",
                likes = 10,
                shares = 1598L,
                views = 1135000L
            ),
            Post(
                id = postId++,
                content = "Привет, это новая Нетология! Пост № 2. Скоро осень.",
                published = "22 мая в 18:36",
                author = "Нетология. Университет интернет-профессий будущего",
                likes = 0,
                shares = 0,
                views = 10
            ),
            Post(
                id = postId++,
                content = "Привет, это новая Нетология! Пост № 3. Тихо на улице, тихо в квартире.",
                published = "25 мая в 18:36",
                author = "Нетология. Университет интернет-профессий будущего",
                likes = 3,
                shares = 8,
                views = 100
            ),
            Post(
                id = postId++,
                content = "Привет, это новая Нетология! Четвертый пост. Выхожу один я на дорогу.",
                published = "26 мая в 18:36",
                author = "Нетология. Университет интернет-профессий будущего",
                likes = 0,
                shares = 0,
                views = 0
            ),
            Post(
                id = postId++,
                content = "Привет, это новая Нетология! А это уже тост!",
                published = "27 мая в 18:36",
                author = "Нетология. Университет интернет-профессий будущего",
                likes = 999,
                shares = 999,
                views = 999
            ),
            Post(
                id = postId++,
                content = "Привет, это новая Нетология! Пост № 5. Мой дядя самых честных правил.",
                published = "28 мая в 18:36",
                author = "Нетология. Университет интернет-профессий будущего",
                likes = 0,
                shares = 0,
                views = 0
            ),
            Post(
                id = postId++,
                content = "Привет, это новая Нетология! Пост № 6. Однажды в студеную зимнюю пору...",
                published = "29 мая в 18:36",
                author = "Нетология. Университет интернет-профессий будущего",
                likes = 0,
                shares = 0,
                views = 10
            )
        ).reversed()

    override fun save(post: Post) {
        if (post.id == 0L) {
            data.value = listOf(post) + data.value.orEmpty()
            return
        }
        data.value = data.value?.map {
            if (it.id == post.id) {
                it.copy(content = post.content)
            } else {
                it
            }
        }
    }

    override val data = MutableLiveData(defaultPosts)

    override fun removeById(id: Long) {
        data.value = data.value?.filter { it.id != id }
    }

    override fun likeById(id: Long) {
        val currentPosts: List<Post> = data.value ?: return
        val result = currentPosts.map {
            if (it.id != id) it else
                it.copy(likedByMe = !it.likedByMe, likes = if (it.likedByMe) it.likes -1 else it.likes +1)
        }
        data.value = result

    }

    override fun shareById(id: Long) {
        val currentPosts: List<Post> = data.value ?: return
        val result = currentPosts.map {
            if (it.id == id) it.copy(shares = it.shares +1) else it
        }
        data.value = result

    }
}
