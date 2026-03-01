package test;

import generators.TestData;
import steps.AuthSteps;
import steps.RegistrationSteps;

public class BaseTest {

    protected TestData testData = new TestData();
    protected RegistrationSteps registrationSteps = new RegistrationSteps();
    protected AuthSteps authSteps = new AuthSteps();
}
