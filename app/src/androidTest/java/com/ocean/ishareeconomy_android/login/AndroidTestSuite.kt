package com.ocean.ishareeconomy_android.login
import com.ocean.ishareeconomy_android.login.login.LoginActivityNoPasswordTest
import com.ocean.ishareeconomy_android.login.login.LoginActivityNoUsernamePasswordTest
import com.ocean.ishareeconomy_android.login.login.LoginActivityNoUsernameTest
import com.ocean.ishareeconomy_android.login.login.LoginActivitySuccessfulLoginTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

// Runs all unit tests.
@RunWith(Suite::class)
@Suite.SuiteClasses(
    LoginActivityNoUsernamePasswordTest::class,
    LoginActivityNoPasswordTest::class,
    LoginActivityNoUsernameTest::class,
    LoginActivitySuccessfulLoginTest::class
)
class AndroidTestSuite