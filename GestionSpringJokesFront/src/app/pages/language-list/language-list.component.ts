import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { LanguageService } from '../../services/language.service';
import { Language } from '../../models/language.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-language-list',
  templateUrl: './language-list.component.html',
  styleUrls: ['./language-list.component.css'],
})
export class LanguageListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'code', 'language', 'acciones'];
  dataSource = new MatTableDataSource<Language>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  filtro: string = '';

  constructor(private languageService: LanguageService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.cargarLanguages();
  }

  cargarLanguages(): void {
    this.languageService.getLanguages().subscribe((language) => {
      this.dataSource.data = language;
      this.dataSource.paginator = this.paginator;
    });
  }

  eliminarLanguage(id: number): void {
    if (confirm('¿Está seguro de que desea eliminar este language?')) {
      this.languageService.deleteLanguage(id).subscribe(() => {
        this.snackBar.open('Language eliminada con éxito', 'Cerrar', { duration: 3000 });
        this.cargarLanguages();
      });
    }
  }

  aplicarFiltro(): void {
    this.dataSource.filter = this.filtro.trim().toLowerCase();
  }
}
