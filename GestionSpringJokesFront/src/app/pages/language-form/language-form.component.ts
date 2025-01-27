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
  selector: 'app-langauge-form',
  templateUrl: './language-form.component.html',
  styleUrls: ['./language-form.component.css'],
})
export class LanguageFormComponent implements OnInit {
  languageForm!: FormGroup;
  id!: number;
  isEditMode: boolean = false;

  constructor(
    private fb: FormBuilder,
    private languageService: LanguageService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.languageForm = this.fb.group({
      code: ['', [Validators.required]],
      language: ['', [Validators.required]],
    });

    this.id = this.route.snapshot.params['id'];
    if (this.id) {
      this.isEditMode = true;
      this.languageService.getLanguage(this.id).subscribe((language) => {
        this.languageForm.patchValue(language);
      });
    }
  }

  guardarLanguage(): void {
    if (this.languageForm.invalid) {
      return;
    }

    const language = this.languageForm.value;
    if (this.isEditMode) {
      this.languageService.updateLanguage(this.id, language).subscribe(() => {
        this.snackBar.open('Language actualizado con éxito', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/languages']);
      });
    } else {
      this.languageService.createLanguage(language).subscribe(() => {
        this.snackBar.open('Language creado con éxito', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/languages']);
      });
    }
  }
}