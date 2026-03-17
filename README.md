------
## Taller 2 - Arquitectura MVVM



### Respuestas a Preguntas Conceptuales

#### 1. ¿Qué problema resuelve el ViewModel en Android?
El ViewModel evita que los datos de la UI se pierdan cuando ocurre un cambio de configuración (como rotar la pantalla). Además, separa la lógica de la interfaz, haciendo el código más organizado y fácil de mantener.

#### 2. ¿Por qué LiveData es "lifecycle-aware" y qué beneficio trae?
LiveData es "lifecycle-aware" porque solo actualiza la UI cuando el componente (Activity o Fragment) está activo. Esto evita errores, fugas de memoria y actualizaciones innecesarias cuando la pantalla no está visible

#### 3. Explica con tus propias palabras el flujo de datos en MVVM
En MVVM, la pantalla no maneja directamente los datos. En lugar de eso, el ViewModel se encarga de preparar la información y la pantalla solo la muestra. Cuando los datos cambian, la pantalla se actualiza automáticamente sin que tengamos que hacer todo manualmente. Esto hace que el código sea más ordenado y fácil de entender.

#### 4. ¿Qué ventaja tiene usar Fragments vs múltiples Activities?
Los Fragments permiten reutilizar componentes de UI dentro de una misma Activity, haciendo la navegación más flexible y eficiente. Además, facilitan el manejo de interfaces adaptables y reducen la cantidad de Activities en la app.

#### 5. ¿Cómo ayuda el Repository Pattern a la arquitectura?
El Repository Pattern centraliza el acceso a los datos (API, base de datos, etc.), separándolo del ViewModel. Esto hace que el código sea más limpio, escalable y fácil de probar.

------

### Diagrama de Arquitectura

<img width="431" height="450" alt="image" src="https://github.com/user-attachments/assets/2632982e-47d0-48c6-914e-2f334a2cebd4" />


### Capturas de Pantalla

![Lista de usuarios](docs/screenshot_list.png)
![Detalle de usuario](docs/screenshot_detail.png)
