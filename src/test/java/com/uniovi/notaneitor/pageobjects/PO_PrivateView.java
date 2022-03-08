package com.uniovi.notaneitor.pageobjects;
import com.uniovi.notaneitor.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PO_PrivateView extends PO_NavView {
    static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep)
    {
        //Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
        SeleniumUtils.waitSeconds(driver, 5);
        //Seleccionamos el alumnos userOrder
        new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
        //Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);
        WebElement score = driver.findElement(By.name("score"));
        score.click();
        score.clear();
        score.sendKeys(scorep);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    static public void checkLogout(WebDriver driver) {
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        clickOption(driver, "logout", "text", loginText);
    }

    static public void checkLogin(WebDriver driver, String usuario, String contraseña, String checkText){
        //Vamos al formulario de login.
        clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, usuario, contraseña);
        //Cmmprobamos que entramos en la pagina privada que queremos
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
    }

    static public List<WebElement> click(WebDriver driver,List<WebElement> elements, String tipo, String text, int page){
        //Pinchamos en la opción de menú de Notas: text
        elements = PO_View.checkElementBy(driver, tipo, text);
        //Hacemos click
        elements.get(page).click();
        return elements;
    }

}
