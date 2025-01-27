import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { JokeListComponent } from './pages/joke-list/joke-list.component';
import { JokeFormComponent } from './pages/joke-form/joke-form.component';
import { FlagListComponent } from './pages/flag-list/flag-list.component';
import { FlagFormComponent } from './pages/flag-form/flag-form.component';
import { CategoryListComponent } from './pages/category-list/category-list.component';
import { CategoryFormComponent } from './pages/category-form/category-form.component';
import { LanguageListComponent } from './pages/language-list/language-list.component';
import {LanguageFormComponent} from './pages/language-form/language-form.component';
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


const routes: Routes = [
  { path: '', redirectTo: 'jokes', pathMatch: 'full' },
  
  { path: 'jokes', component: JokeListComponent },
  { path: 'jokes/nuevo', component: JokeFormComponent },
  { path: 'jokes/editar/:id', component: JokeFormComponent },

  { path: 'flags', component: FlagListComponent },
  { path: 'flags/nuevo', component: FlagFormComponent },
  { path: 'flags/editar/:id', component: FlagFormComponent },

  { path: 'categories', component: CategoryListComponent },
  { path: 'categories/nuevo', component: CategoryFormComponent },
  { path: 'categories/editar/:id', component: CategoryFormComponent },

  { path: 'languages', component: LanguageListComponent },
  { path: 'languages/nuevo', component: LanguageFormComponent },
  { path: 'languages/editar/:id', component: LanguageFormComponent },

  { path: 'types', component: TypeListComponent },
  { path: 'types/nuevo', component: TypeFormComponent },
  { path: 'types/editar/:id', component: TypeFormComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes),
    MatTableModule,
    MatPaginatorModule,
    MatSnackBarModule,
    MatDialogModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    RouterModule
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
