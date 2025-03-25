# 📱 Aplicación de Expensas

¡Bienvenido! 🚀 Estoy desarrollando una **aplicación de expensas** como parte de mi aprendizaje de Kotlin Multiplatform. Este proyecto es mi primer acercamiento al desarrollo multiplataforma con tecnologías modernas de Android.

## 🛠️ Tecnologías y Arquitectura

Estoy explorando y aplicando:

- **Kotlin Multiplatform (KMP)**: Para compartir código entre Android e iOS.
- **Jetpack Compose**: Para construir una interfaz de usuario declarativa y moderna en Android.
- **Arquitectura MVVM**: Para una mejor separación de responsabilidades y escalabilidad.
- **API Rest**: Consumo de datos desde un backend.
- **Base de datos SQLDelight**: Para almacenamiento local con SQL tipado en Kotlin.

## 📖 Aprendizaje y Referencias

Este proyecto forma parte de mi proceso de aprendizaje y experimentación. Estoy adquiriendo conocimientos a través de **Udemy**, donde sigo cursos especializados en Kotlin Multiplatform y desarrollo de aplicaciones modernas.

## 🔄 Avances Recientes

### Capa de Datos:

- Lista de expensas con datos mockeados para visualizar en el preview.
- **ExpenseManager** para manejar la lista y los métodos de agregar y editar expensas, y obtener las categorías.
- **ExpenseRepository** como interfaz para implementar los métodos mencionados anteriormente.
- **ExpenseRepoImpl** que extiende nuestra interfaz, utilizando los métodos desarrollados en **ExpenseManager**.
- (Extra) **Método `deleteExpense` agregado** (tarea por fuera del curso).
- Principios de **inyección de dependencia manual** en **ExpenseRepoImpl**.

### Capa de Presentación:

- Creación de **ExpensesViewModel**.
- En **ExpensesScreen** se implementó **uiState**.
- Ejemplos de esquemas tanto **MVVM** como **Corrutinas**.

### Configuración de Top Bar:

- **TitleTopBarTypes** para diferenciar y poner título según la pantalla.
- En **App**, se desarrolló toda la lógica del **Scaffold (top bar)**.

### ExpenseDetailScreen:

- Desarrollo completo de la pantalla **ExpenseDetailScreen**.
- Aplicación funcional con la navegación adecuada.

### Unit Test:

- Agregada dependencia para **unit tests**.
- Creación de tests de ejemplo y funcionales para el **Repo**.

## ⚙️ Trabajo en Progreso

Se estará trabajando en:

- **Inyección de Dependencias**.
- **Base de Datos SQLDelight**.
- **API REST con KTOR + KMP**.
- **Integración con Gemini AI**.

---

Este README evolucionará a medida que avance en mi aprendizaje y el proyecto tome forma. ¡Cualquier sugerencia o consejo es bienvenido! 😊
