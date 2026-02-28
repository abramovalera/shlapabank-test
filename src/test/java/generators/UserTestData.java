package generators;

import net.datafaker.Faker;

public class UserTestData {

    Faker faker = new Faker();


    public String login() {
        return faker.regexify("[A-Za-z0-9]{6,20}");
    }

    public String password() {
        return faker.credentials().password
                (8, 30, true, true);
    }
}
