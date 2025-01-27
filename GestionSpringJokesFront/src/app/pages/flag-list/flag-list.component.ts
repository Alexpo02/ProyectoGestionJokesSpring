import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { FlagService } from '../../services/flag.service';
import { Flag } from '../../models/flag.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-flag-list',
  templateUrl: './flag-list.component.html',
  styleUrls: ['./flag-list.component.css'],
})
export class FlagListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'flag', 'acciones'];
  dataSource = new MatTableDataSource<Flag>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  filtro: string = '';

  constructor(private flagService: FlagService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.cargarFlags();
  }

  cargarFlags(): void {
    this.flagService.getFlags().subscribe((flags) => {
      this.dataSource.data = flags;
      this.dataSource.paginator = this.paginator;
    });
  }

  eliminarFlag(id: number): void {
    if (confirm('¿Está seguro de que desea eliminar esta flag?')) {
      this.flagService.deleteFlag(id).subscribe(() => {
        this.snackBar.open('Flag eliminada con éxito', 'Cerrar', { duration: 3000 });
        this.cargarFlags();
      });
    }
  }

  aplicarFiltro(): void {
    this.dataSource.filter = this.filtro.trim().toLowerCase();
  }
}
