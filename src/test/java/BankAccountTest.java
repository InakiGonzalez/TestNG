import org.example.model.BankAccount;
import org.testng.Assert;
import org.testng.annotations.*;

public class BankAccountTest {
    private static BankAccount bankAccount;

    // 2. Test Lifecycle Hooks
    @BeforeClass
    public void setUpClass() {
        bankAccount = new BankAccount(0.0);
    }

    @AfterClass
    public void tearDownClass() {
        bankAccount = null;
    }

    // Basic Testing with @Test and Assertions
    @Test(groups = {"positive-tests"})
    public void testDepositPositive() {
        bankAccount = new BankAccount(0.0);
        bankAccount.deposit(10.0);
        bankAccount.deposit(5.0);
        Assert.assertEquals(bankAccount.getBalance(), 15.0, 0.0);
    }

    // Test Groups: negative
    @Test(groups = {"negative-tests"})
    public void testDepositNegative() {
        bankAccount.deposit(10.0);
        Assert.assertNotEquals(bankAccount.getBalance(), 100.0, 0.0); // wrong expected
    }

    // Exception Testing
    @Test(expectedExceptions = IllegalArgumentException.class, groups = {"negative-tests"})
    public void testWithdrawMoreThanBalance() {
        bankAccount.withdraw(1000.0); // too much
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = {"negative-tests"})
    public void testDepositZero() {
        bankAccount.deposit(0.0);
    }

    // Data-Driven Testing
    @Test(dataProvider = "transactionValues", groups = {"positive-tests"})
    public void testMultipleDeposits(double amount, double expectedBalance) {
        bankAccount = new BankAccount(0.0); // reset for clean test
        bankAccount.deposit(amount);
        Assert.assertEquals(bankAccount.getBalance(), expectedBalance, 0.0);
    }

    @DataProvider(name = "transactionValues")
    public Object[][] depositData() {
        return new Object[][]{
                {10.0, 10.0},
                {20.0, 20.0},
                {100.0, 100.0}
        };
    }

    // 6. Test Dependencies
    @Test
    public void successfulDeposit() {
        bankAccount = new BankAccount(0.0);
        bankAccount.deposit(50.0);
        Assert.assertEquals(bankAccount.getBalance(), 50.0, 0.0);
    }

    @Test(dependsOnMethods = {"successfulDeposit"})
    public void withdrawAfterDeposit() {
        bankAccount.withdraw(20.0);
        Assert.assertEquals(bankAccount.getBalance(), 80.0, 0.0);
    }


}