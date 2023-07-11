package com.apex.codeassesment.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apex.codeassesment.data.UserRepository
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.State
import com.apex.codeassesment.data.remote.mapper.RemoteToLocalDataMapper.toUser
import com.apex.codeassesment.data.remote.model.Users
import com.apex.codeassesment.databinding.ActivityMainBinding
import com.apex.codeassesment.di.MainComponent
import com.apex.codeassesment.di.MyViewModelFactory
import com.apex.codeassesment.ui.Utils.Companion.openActivity
import com.apex.codeassesment.ui.details.DetailsActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO (5 points): Move calls to repository to Presenter or ViewModel.
// TODO (5 points): Use combination of sealed/Dataclasses for exposing the data required by the view from viewModel .
// TODO (3 points): Add tests for view-model or presenter.
// TODO (1 point): Add content description to images
// TODO (3 points): Add tests
// TODO (Optional Bonus 10 points): Make a copy of this activity with different name and convert the current layout it is using in
//  Jetpack Compose.
class MainActivity : AppCompatActivity() {

    private lateinit var usersAdapter: UsersAdapter
    @Inject
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: MainViewModel
    // TODO (2 points): Convert to view binding
    private lateinit var mBinder: ActivityMainBinding

    private var randomUser: User = User()
        set(value) {
            // TODO (1 point): Use Glide to load images after getting the data from endpoints mentioned in RemoteDataSource
            mBinder.mainName.text = value.name?.first
            mBinder.mainEmail.text = value.email
            mBinder.mainImage.let { Glide.with(this).load(value.picture?.medium).into(it) }
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinder.root)
        (applicationContext as MainComponent.Injector).mainComponent.inject(this)
        viewModel =
            ViewModelProvider(this, MyViewModelFactory(userRepository))[MainViewModel::class.java]
        randomUser = userRepository.getSavedUser()

        mBinder.mainSeeDetailsButton.setOnClickListener { navigateDetails(randomUser) }

        mBinder.mainRefreshButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getUser()
                    .collect {
                        when (it) {
                            is State.DataState -> {
                                mBinder.progressBar.visibility= View.GONE
                                randomUser = it.data
                            }
                            is State.ErrorState -> {
                                mBinder.progressBar.visibility= View.GONE
                                it.exception.message?.let { it1 ->
                                    Snackbar.make(mBinder.root,
                                        it1, Snackbar.LENGTH_SHORT).show()
                                }
                            }
                            is State.LoadingState -> {
                                mBinder.progressBar.visibility= View.VISIBLE
                            }
                        }

                    }
            }
        }
        mBinder.mainUserListButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getUsers()
                    .collect {
                        when (it) {
                            is State.DataState -> {
                                mBinder.progressBar.visibility= View.GONE
                                usersAdapter = UsersAdapter(getUsersList(it.data))
                                mBinder.mainUserList.apply {
                                    this.layoutManager=LinearLayoutManager(this@MainActivity)
                                    adapter = usersAdapter
                                }
                            }

                            is State.ErrorState -> {
                                mBinder.progressBar.visibility= View.GONE
                                it.exception.message?.let { it1 ->
                                    Snackbar.make(mBinder.root,
                                        it1, Snackbar.LENGTH_SHORT).show()
                                }
                            }
                            is State.LoadingState -> {
                                mBinder.progressBar.visibility= View.VISIBLE
                            }
                        }

                    }

            }
        }
    }

    private fun getUsersList(users: Users): List<User> {
        val usersList = ArrayList<User>()
        if (!users.resps.isNullOrEmpty()) {
            users.resps.forEach {
                usersList.add(it.toUser())
            }
        }
        return usersList
    }

    // TODO (2 points): Convert to extension function.
    private fun navigateDetails(user: User) {
        openActivity(DetailsActivity::class.java) {
            putParcelable("saved-user-key", user)
        }
    }

}
