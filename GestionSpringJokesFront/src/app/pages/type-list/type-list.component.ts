import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { TypeService } from '../../services/type.service';
import { Type } from '../../models/type.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-flag-list',
  templateUrl: './type-list.component.html',
  styleUrls: ['./type-list.component.css'],
})
export class TypeListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'type', 'acciones'];
  dataSource = new MatTableDataSource<Type>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  filtro: string = '';

  constructor(private typeService: TypeService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.cargarTypes();
  }

  cargarTypes(): void {
    this.typeService.getTypes().subscribe((types) => {
      this.dataSource.data = types;
      this.dataSource.paginator = this.paginator;
    });
  }

  eliminarType(id: number): void {
    if (confirm('¿Está seguro de que desea eliminar este type?')) {
      this.typeService.deleteType(id).subscribe(() => {
        this.snackBar.open('Type eliminado con éxito', 'Cerrar', { duration: 3000 });
        this.cargarTypes();
      });
    }
  }

  aplicarFiltro(): void {
    this.dataSource.filter = this.filtro.trim().toLowerCase();
  }
}
