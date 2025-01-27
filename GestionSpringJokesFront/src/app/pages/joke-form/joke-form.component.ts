import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { JokeService } from '../../services/joke.service';
import { CategoryService } from '../../services/category.service';
import { LanguageService } from '../../services/language.service';
import { TypeService } from '../../services/type.service';
import { FlagService } from '../../services/flag.service';

import { Joke } from '../../models/joke.model';
import { Category } from '../../models/category.model';
import { Language } from '../../models/language.model';
import { Type } from '../../models/type.model';
import { Flag } from '../../models/flag.model';

@Component({
  selector: 'app-joke-form',
  templateUrl: './joke-form.component.html',
  styleUrls: ['./joke-form.component.css'],
})
export class JokeFormComponent implements OnInit {
  jokeForm!: FormGroup;
  categories: Category[] = [];
  languages: Language[] = [];
  types: Type[] = [];
  flags: Flag[] = [];
  id!: number;
  isEditMode: boolean = false;

  constructor(
    private fb: FormBuilder,
    private jokeService: JokeService,
    private categoryService: CategoryService,
    private languageService: LanguageService,
    private typeService: TypeService,
    private flagService: FlagService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    // Inicializar el formulario
    this.jokeForm = this.fb.group({
      category: [null, [Validators.required]],
      language: [null, [Validators.required]],
      type: [null, [Validators.required]],
      text1: ['', [Validators.required]],
      text2: ['', []], // Inicialmente no requerido
      flagses: [[], []], // Campo para múltiples flags
    });
  
    // Manejar cambios en el campo "type"
    this.jokeForm.get('type')?.valueChanges.subscribe((type) => {
      if (type.type === 'single') {
        this.jokeForm.get('text2')?.disable();
        this.jokeForm.get('text2')?.clearValidators();
      } else if (type.type === 'twopart') {
        this.jokeForm.get('text2')?.enable();
        this.jokeForm.get('text2')?.setValidators([Validators.required]);
      }
      this.jokeForm.get('text2')?.updateValueAndValidity();
    });
  
    // Cargar datos dinámicos
    this.loadCategories();
    this.loadLanguages();
    this.loadTypes();
    this.loadFlags();
  
    // Cargar datos en modo edición
    this.id = this.route.snapshot.params['id'];
    if (this.id) {
      this.isEditMode = true;
      this.jokeService.getJoke(this.id).subscribe((joke: Joke) => {
        this.jokeForm.patchValue({
          category: joke.categories,
          language: joke.language,
          type: joke.types,
          text1: joke.text1,
          text2: joke.text2,
          flagses: joke.flagses || [],
        });
      });
    }
  }
  

  guardarJoke(): void {
    if (this.jokeForm.invalid) {
      return;
    }
  
    const joke: Joke = {
      ...this.jokeForm.value,
      categories: this.jokeForm.value.category,
      language: this.jokeForm.value.language,
      types: this.jokeForm.value.type,
      flagses: this.jokeForm.value.flagses,
      text2: this.jokeForm.value.type.type === 'single' ? null : this.jokeForm.value.text2, // No enviar text2 si el tipo es "single"
    };
  
    if (this.isEditMode) {
      this.jokeService.updateJoke(this.id, joke).subscribe(() => {
        this.snackBar.open('Joke actualizada con éxito', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/jokes']);
      });
    } else {
      this.jokeService.createJoke(joke).subscribe(() => {
        this.snackBar.open('Joke creada con éxito', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/jokes']);
      });
    }
  }
  
  
  

  private loadCategories(): void {
    this.categoryService.getCategories().subscribe((categories) => (this.categories = categories));
  }

  private loadLanguages(): void {
    this.languageService.getLanguages().subscribe((languages) => (this.languages = languages));
  }

  private loadTypes(): void {
    this.typeService.getTypes().subscribe((types) => (this.types = types));
  }

  private loadFlags(): void {
    this.flagService.getFlags().subscribe((flags) => (this.flags = flags));
  }
}
