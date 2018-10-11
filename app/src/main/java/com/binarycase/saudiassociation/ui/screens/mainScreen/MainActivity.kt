package com.binarycase.saudiassociation.ui.screens.mainScreen

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.multidex.MultiDex
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.binarycase.saudiassociation.R
import com.binarycase.saudiassociation.loginRegister.data.CacheUtils
import com.binarycase.saudiassociation.loginRegister.ui.activity.SignInActivity
import com.binarycase.saudiassociation.utils.ContextWrapper
import com.binarycase.saudiassociation.utils.Localization_Utils
import com.binarycase.saudiassociation.ui.screens.fragments.staticContentScreens.CallUsFragment
import com.binarycase.saudiassociation.ui.screens.fragments.categorySubList.CategorySubListFragment
import com.binarycase.saudiassociation.ui.screens.fragments.mainSections.MainSectionsFragment
import com.binarycase.saudiassociation.ui.screens.fragments.searchCategSubList.SearchCategoryFragment
import com.binarycase.saudiassociation.ui.screens.fragments.staticContentScreens.PolicyFragment
import com.binarycase.saudiassociation.ui.screens.fragments.staticContentScreens.WhoAreWeFragment
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nvView.itemIconTintList = null
        setupMenuItemListeners()
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.nav_menu)
            setTitle(R.string.main_sections_title)
            setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.action_bar)))

        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContent, MainSectionsFragment(), FRAGMENT_MAIN_SECTIONS)
                .commit()

        searchBt.setOnClickListener {
            search()
        }
    }

    val FRAGMENT_WHO_ARE_WE = "FRAGMENT_WHO_ARE_WE"
    val FRAGMENT_POLICY = "FRAGMENT_POLICY"
    val FRAGMENT_CALLUS = "FRAGMENT_CALLUS"
    val FRAGMENT_MAIN_SECTIONS = "FRAGMENT_MAIN_SECTIONS"
    val FRAGMENT_WORDS_FAVOURITE = "FRAGMENT_WORDS_FAVOURITE"

    private fun setupMenuItemListeners() {
        nvView.setNavigationItemSelectedListener {
            it.isChecked = true
            drawer_layout.closeDrawers()
            if (it.itemId == R.id.nav_search) {
                searchInToolbarContainer.visibility = VISIBLE
            } else if (it.itemId == R.id.nav_logout) {
                CacheUtils.clearCache(this)
                startActivity(Intent(this@MainActivity,SignInActivity::class.java))
                finish()
            }
            else {
                searchInToolbarContainer.visibility = GONE
            }
            when (it.itemId) {
                R.id.nav_who_are_we -> {
                    openFragment(FRAGMENT_WHO_ARE_WE)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_all_sections -> {
                    openFragment(FRAGMENT_MAIN_SECTIONS)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_favourite -> {
                    openFragment(FRAGMENT_WORDS_FAVOURITE)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_policy -> {
                    openFragment(FRAGMENT_POLICY)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_call_us -> {
                    openFragment(FRAGMENT_CALLUS)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_search -> {
                    openFragment(SearchCategoryFragment.Tag)
                    return@setNavigationItemSelectedListener true
                }
                else -> {
                    return@setNavigationItemSelectedListener false
                }
            }
        }
    }

    private fun toggleDrawer() {
        if (drawer_layout.isDrawerOpen(Gravity.START)) {
            drawer_layout.closeDrawers()
        } else {
            drawer_layout.openDrawer(Gravity.START)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                toggleDrawer()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun getResString(stringId: Int): String {
        return resources.getString(stringId)
    }

    private fun openFragment(fragmentTag: String) {
        val fragment: Fragment?
        if (fragmentTag == SearchCategoryFragment.Tag) {
            fragment = SearchCategoryFragment()
        }else {
            fragment = supportFragmentManager.findFragmentByTag(fragmentTag) ?: when (fragmentTag) {
                FRAGMENT_WHO_ARE_WE -> {
                    supportActionBar?.title = getResString(R.string.who_are_we)
                    WhoAreWeFragment()
                }
                FRAGMENT_POLICY -> {
                    supportActionBar?.title = getResString(R.string.usage_policy)
                    PolicyFragment()

                }
                FRAGMENT_CALLUS -> {
                    supportActionBar?.title = getResString(R.string.call_us)
                    CallUsFragment()
                }
                FRAGMENT_MAIN_SECTIONS -> {
                    supportActionBar?.title = getResString(R.string.main_sections_title)
                    MainSectionsFragment()
                }
                FRAGMENT_WORDS_FAVOURITE -> {
                    CategorySubListFragment.newInstance(0, getResString(R.string.favourite))
                }

                else -> {
                    supportActionBar?.title = getResString(R.string.main_sections_title)
                    MainSectionsFragment()
                }
            }
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContent, fragment, fragmentTag)
                .addToBackStack(null)
                .commit()
    }

    override fun attachBaseContext(newBase: Context?) {
        val context = ContextWrapper.wrap(newBase!!, "ar")
        Localization_Utils.changeLang(context, "ar")
        MultiDex.install(this)
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context))

    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0 && supportFragmentManager.findFragmentByTag(FRAGMENT_MAIN_SECTIONS) != null) {
            Log.i("MainActivity", "popping backstack")
            fm.popBackStack()
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super")
            super.onBackPressed()
        }
    }

    fun search() {
        KeyboardUtils.hideSoftInput(this@MainActivity)
        val fragment: SearchCategoryFragment?
        try {
            fragment = supportFragmentManager.findFragmentByTag(SearchCategoryFragment.Tag) as SearchCategoryFragment
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        if (fragment == null) return
        if (searchField.text.isNullOrBlank()) {
            ToastUtils.showShort("من فضلك ادخل كلمات البحث")
        } else {
            fragment.searchFor(searchField.text.toString())
        }

    }

    fun clearSearchField() {
        searchField.setText("")
    }

}
