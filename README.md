# 🌧️ Árbol de Precipitaciones

## 📘 Descripción

El sistema gestiona mediciones de precipitaciones diarias organizadas por:

- **Campo (zona geográfica)**
- **Año y mes (periodo)**
- **Día del mes**

Se implementa un **Árbol Binario de Búsqueda (ABB)** donde cada nodo representa un campo. Cada campo contiene un diccionario que asocia un periodo con las precipitaciones por día del mes correspondiente.

### Funcionalidades principales

- Agregar / eliminar campos
- Agregar / eliminar mediciones diarias
- Obtener:
    - Periodos registrados
    - Lluvias por campo y mes
    - Lluvias promedio por día
    - Campo más lluvioso
    - Mes más lluvioso
    - Campos con lluvia superior al promedio

## 📁 Estructura

- `tdas/`: Interfaces provistas por la cátedra (no modificables)
- `implementacion/`: Implementaciones dinámicas de los TDA
- `algoritmos/`: Algoritmos auxiliares para resolver consultas avanzadas
- `test/`: Casos de prueba y verificación

## 🔧 Tecnologías

- Lenguaje: **Java**
- IDE: **IntelliJ IDEA**
- No requiere librerías externas

## 📈 Complejidad Algorítmica

| Método                             | Complejidad       |
|-----------------------------------|-------------------|
| agregarMedicion / eliminarMedicion | O(log n + m)     |
| eliminarCampo                     | O(log n + p * m)  |
| medicionesMes                     | O(n * m)          |
| medicionesCampoMes                | O(log n + m)      |
| mesMasLluvioso                    | O(n * p * m)      |
| campoMasLLuvisoHistoria           | O(n * p * m)      |
| promedioLluviaEnUnDia             | O(n)              |
| camposConLLuviaMayorPromedio     | O(n * m)          |