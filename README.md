## O2App
The focus of this application is to showcase software engineering in the form of a minimum viable product (MVP). This includes both UX and UI design.
<table width="100%">
  <tr align="center">
  <th width="25%">Home</th>
  <th width="25%">Generate code</th>
  <th width="25%">Activate code</th>
  <th width="25%">Code active</th>
  </tr>
 <tr>
  <td width="25%">
   <img src="https://github.com/user-attachments/assets/36186560-8cc3-4388-8fbc-33bcff1795e9" alt="O2 App Home" />
  </td>
  <td width="25%">
   <img src="https://github.com/user-attachments/assets/5bfe6ef4-8a1c-4529-9f6b-65ca8b7fb666" alt="O2 App Generate code" />
  </td>
  <td width="25%">
   <img src="https://github.com/user-attachments/assets/3190c5e2-731a-4edc-a037-055a9398f0ba" alt="O2 App Active code"/>
  </td>
  <td width="25%">
   <img src="https://github.com/user-attachments/assets/d1677b9b-33b4-4544-b443-2eed45f88e8e" alt="O2 App Code active"/>
  </td>
  </tr>
</table>

### Project structure
 - Single module architecture divided by features.
 - Clean architecture Presentation->Domain<-Data.
 - Presentation layer -> Reacts to user interaction and provides the most actual visible content (state) for the user.
 - Domain layer -> Free of any external dependencies and contains only pure business logic and may include models and abstractions.
 - Data layer -> Contains concrete implementations and maps DTOs into domain models.
 - Presentation layer follows MVI and UDF.
 - Core package can be accessed across the app - and it contains logic shared among the different features and components.
 - App package links everything together - such as navigation and di modules.

### Versioning
 - The **master** branch is always up to date.
