# Wordle - Juego educativo de programación

Este proyecto es una implementación completamente funcional del popular juego Wordle. Está diseñado para reforzar el aprendizaje de programación y lógica aplicada, mientras se disfruta de una experiencia interactiva y educativa. ¡Descubre palabras y diviértete jugando!

## Descripción del Proyecto

El juego permite:
- Adivinar palabras ocultas en un máximo de 5 intentos.
- Recibir retroalimentación visual sobre qué letras están correctas, presentes o ausentes.
- Utilizar pistas breves asociadas a cada palabra como apoyo educativo.

El proyecto está desarrollado en **Java**, utilizando las librerías estándar de Swing para la interfaz gráfica.

El propósito de incluir este proyecto en tu portafolio es mostrar habilidades en:
- Manejo de interfaces gráficas (Swing).
- Estructuración de código orientada a objetos.
- Uso básico de lógica de videojuegos.
- Validación y procesamiento de datos de entrada del usuario.

## Características

1. **Interfaz gráfica interactiva (GUI)**:
   - Uso de colores visuales para indicar el estado de las letras (correctas, presentes, ausentes).
   - Diseño dinámico: se adapta a la longitud de la palabra seleccionada.

2. **Componentes principales del juego**:
   - **WordleGame.java**:
     Contiene la lógica principal del juego, como la validación de palabras y la selección de la palabra oculta.
   - **WordleUI.java**: 
     Administra y muestra la interfaz gráfica, incluyendo el tablero de juego y los mensajes de estado.
   - **Word.java**:
     Clase que representa cada palabra con su descripción asociada.

3. **Ejemplos educativos**:
   Las palabras en el juego están relacionadas con terminología de programación, lo que lo convierte en una herramienta divertida para aprender conceptos clave como: objetos, bucles, arrays, etc.


### Requisitos previos
- JDK 8 o superior instalado en tu máquina.
- Un editor/IDE como NetBeans, IntelliJ IDEA o Eclipse.
- Opcionalmente, ANT (si no estás usando un IDE).

### Instrucciones para ejecutar
1. Clona el repositorio:
   ```bash
   git clone git@github.com:miguelfdez03/Wordle.git
   ```
2. Abre el proyecto en tu IDE favorito.
3. Compila y ejecuta el archivo principal `WordleUI.java`.

El juego se iniciará y estará listo para jugar.

## Contribuciones

Si quieres contribuir con ideas, correcciones o mejoras, eres bienvenido. Simplemente abre un `pull request` después de clonar el repositorio o reporta un issue en GitHub.

## Licencia

Este proyecto es de libre uso para fines educativos o como parte del portafolio personal. Sin embargo, no se permite el uso comercial sin permiso explícito.
