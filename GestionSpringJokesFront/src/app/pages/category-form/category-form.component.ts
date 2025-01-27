import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CategoryService } from '../../services/category.service';


@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.css'],
})
export class CategoryFormComponent implements OnInit {
  categoryForm!: FormGroup;
  id!: number;
  isEditMode: boolean = false;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.categoryForm = this.fb.group({
      category: ['', [Validators.required]],
    });

    this.id = this.route.snapshot.params['id'];
    if (this.id) {
      this.isEditMode = true;
      this.categoryService.getCategory(this.id).subscribe((category) => {
        this.categoryForm.patchValue(category);
      });
    }
  }

  guardarCategory(): void {
    if (this.categoryForm.invalid) {
      return;
    }

    const category = this.categoryForm.value;
    if (this.isEditMode) {
      this.categoryService.updateCategory(this.id, category).subscribe(() => {
        this.snackBar.open('Category actualizada con éxito', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/categories']);
      });
    } else {
      this.categoryService.createCategory(category).subscribe(() => {
        this.snackBar.open('Category creada con éxito', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/categories']);
      });
    }
  }
}