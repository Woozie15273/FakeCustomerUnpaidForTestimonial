#  Fake Customer Persona Generator

## Introduction

This project generates **fake customer personas** that can be used as **web application test data** or as content for the **testimonial section** of online portfolio pages.

The output is provided in **JSON format**, and the schema includes the following fields:

* `name`
* `email`
* `dob` (Date of Birth)
* `address`
* `password`
* `avatar` (Image URL)
* `testimonial` (Optional text)

---

## Workflow and Architecture

The data generation process is broken down into three main phases: Before, During, and After.

### 1. Configuration

* The source of user portraits (avatars), the total amount of data to generate, and the output folder path can be configured in `/resources/Config`.
* The source and content of testimonials can be updated and managed within `/resources/Testimonial`.

### 2. Execution

* `/utility/Util` is the central class responsible for **reading and passing configurations**.
* `/model/UserProfile` serves as the **main constructor** for a single user persona object.
* `/model/Testimonial` handles the logic to **read and pass the content of testimonials**.
* `/service/DataGenerator` acts as the **main assembly line**. It assembles the final data and currently allows for future customization of output format and file name.

### 3. Output

* The `.json` file containing the designated amount of data will be generated into the given path (set to `/output` by default).

---

## Known Limitations

* **Localization Dropped:** Localization features were unfortunately dropped as a function. As a Chinese native, I can confirm that the generated Chinese addresses, in particular, will **not pass validation** on most modern Chinese applications.

---

## To-Do List

The next update will focus on integrating a small-scale **Selenium demo** to fill in the generated data on one of the available automation QA demo websites (site to be determined).

---

## Appendix

**Development Environment:**

* `java -v`: Temurin-25

**Inspiration and Resources:**

* [randomuser.me](https://randomuser.me/)
* Datafaker: [https://www.datafaker.net/](https://www.datafaker.net/)
* java-faker: [https://github.com/DiUS/java-faker](https://github.com/DiUS/java-faker)
* [This Person Does Not Exist](https://thispersondoesnotexist.com/)