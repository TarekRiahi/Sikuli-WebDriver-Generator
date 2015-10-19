package se.poc.sikuli.webdriver.support;

public @interface StateMapping {
    public String url() default "";
    public String state() default "default";
    Score minScore() default Score.GOOD;
}
