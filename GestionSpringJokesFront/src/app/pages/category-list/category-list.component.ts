import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../models/category.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css'],
})
export class CategoryListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'category', 'acciones'];
  dataSource = new MatTableDataSource<Category>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  filtro: string = '';

  constructor(private categoryService: CategoryService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.cargarCategories();
  }

  cargarCategories(): void {
    this.categoryService.getCategories().subscribe((category) => {
      this.dataSource.data = category;
      this.dataSource.paginator = this.paginator;
    });
  }

  eliminarCategory(id: number): void {
    if (confirm('¿Está seguro de que desea eliminar esta category?')) {
      this.categoryService.deleteCategory(id).subscribe(() => {
        this.snackBar.open('Category eliminada con éxito', 'Cerrar', { duration: 3000 });
        this.cargarCategories();
      });
    }
  }

  aplicarFiltro(): void {
    this.dataSource.filter = this.filtro.trim().toLowerCase();
  }
}
