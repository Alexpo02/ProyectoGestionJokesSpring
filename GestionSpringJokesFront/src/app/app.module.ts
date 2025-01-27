import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { JokeListComponent } from './pages/joke-list/joke-list.component';
import { JokeFormComponent } from './pages/joke-form/joke-form.component';
import { FlagListComponent } from './pages/flag-list/flag-list.component';
import { FlagFormComponent } from './pages/flag-form/flag-form.component';
import { CategoryFormComponent } from './pages/category-form/category-form.component';
import { CategoryListComponent } from './pages/category-list/category-list.component';
import { LanguageListComponent } from './pages/language-list/language-list.component'; 
import { LanguageFormComponent } from './pages/language-form/language-form.component'; 
import { TypeListComponent } from './pages/type-list/type-list.component'; 
import { TypeFormComponent } from './pages/type-form/type-form.component'; 

// Angular Material
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { NavbarComponent } from './components/navbar/navbar.component'; // Importación necesaria para mat-select

@NgModule({
  declarations: [
    AppComponent,
    JokeListComponent,
    JokeFormComponent,
    FlagListComponent,
    FlagFormComponent,
    CategoryFormComponent,
    CategoryListComponent,
    LanguageListComponent,
    LanguageFormComponent,
    TypeListComponent,
    TypeFormComponent,
    NavbarComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSnackBarModule,
    MatDialogModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
