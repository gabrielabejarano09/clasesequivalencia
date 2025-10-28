# Defectos

## Defecto #1
**Caso:** `RegistryTest.java - shouldNotRegisterPersonUnderAge()`
**Descripción:**  
El sistema permite registrar a una persona menor de edad, aunque debería rechazarla.
**Resultado esperado:**  
El método `registerPerson()` debe lanzar una excepción o retornar `false` cuando la edad sea menor a 18.
**Resultado obtenido:**  
El método retorna `true` y completa el registro del usuario menor de edad.
**Causa probable:**  
No existe validación de edad o la condición se implementó incorrectamente (`<= 18` en lugar de `< 18`).
**Estado:**  
 **Abierto**

## Defecto #2
**Caso de prueba:** `No se podian ejecutar los test`
**Descripción:**  
La aplicación falla al intentar hacer los test.
**Resultado esperado:**  
Se deberian ejecutar los test de forma eficiente.
**Resultado obtenido:**  
Se lanza la excepción `FileNotFoundException`.
**Causa probable:**  
Configuración incorrecta en POM

**Estado:**  
**Cerrado**
