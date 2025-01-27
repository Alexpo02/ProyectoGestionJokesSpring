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
  selector: 'app-flag-form',
  templateUrl: './flag-form.component.html',
  styleUrls: ['./flag-form.component.css'],
})
export class FlagFormComponent implements OnInit {
  flagForm!: FormGroup;
  id!: number;
  isEditMode: boolean = false;

  constructor(
    private fb: FormBuilder,
    private flagService: FlagService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.flagForm = this.fb.group({
      flag: ['', [Validators.required]],
    });

    this.id = this.route.snapshot.params['id'];
    if (this.id) {
      this.isEditMode = true;
      this.flagService.getFlag(this.id).subscribe((flag) => {
        this.flagForm.patchValue(flag);
      });
    }
  }

  guardarFlag(): void {
    if (this.flagForm.invalid) {
      return;
    }

    const flag = this.flagForm.value;
    if (this.isEditMode) {
      this.flagService.updateFlag(this.id, flag).subscribe(() => {
        this.snackBar.open('Flag actualizada con éxito', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/flags']);
      });
    } else {
      this.flagService.createFlag(flag).subscribe(() => {
        this.snackBar.open('Flag creada con éxito', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/flags']);
      });
    }
  }
}