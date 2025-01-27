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
  selector: 'app-type-form',
  templateUrl: './type-form.component.html',
  styleUrls: ['./type-form.component.css'],
})
export class TypeFormComponent implements OnInit {
  typeForm!: FormGroup;
  id!: number;
  isEditMode: boolean = false;

  constructor(
    private fb: FormBuilder,
    private typeService: TypeService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.typeForm = this.fb.group({
      type: ['', [Validators.required]],
    });

    this.id = this.route.snapshot.params['id'];
    if (this.id) {
      this.isEditMode = true;
      this.typeService.getType(this.id).subscribe((type) => {
        this.typeForm.patchValue(type);
      });
    }
  }

  guardarType(): void {
    if (this.typeForm.invalid) {
      return;
    }

    const type = this.typeForm.value;
    if (this.isEditMode) {
      this.typeService.updateType(this.id, type).subscribe(() => {
        this.snackBar.open('Type actualizado con éxito', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/types']);
      });
    } else {
      this.typeService.createType(type).subscribe(() => {
        this.snackBar.open('Type creado con éxito', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/types']);
      });
    }
  }
}