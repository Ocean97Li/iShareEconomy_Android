package com.ocean.ishareeconomy_android.ocean
import com.ocean.ishareeconomy_android.ocean.lending.LendingActivityAddThenDeleteObjectTest
import com.ocean.ishareeconomy_android.ocean.login.LoginActivityNoPasswordTest
import com.ocean.ishareeconomy_android.ocean.login.LoginActivityNoUsernamePasswordTest
import com.ocean.ishareeconomy_android.ocean.login.LoginActivityNoUsernameTest
import com.ocean.ishareeconomy_android.ocean.login.LoginActivitySuccessfulLoginTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

// Runs all UI tests.
@RunWith(Suite::class)
@Suite.SuiteClasses(
    LoginActivityNoUsernamePasswordTest::class,
    LoginActivityNoPasswordTest::class,
    LoginActivityNoUsernameTest::class,
    LoginActivitySuccessfulLoginTest::class,
    LendingActivityAddThenDeleteObjectTest::class
)
class AndroidTestSuite