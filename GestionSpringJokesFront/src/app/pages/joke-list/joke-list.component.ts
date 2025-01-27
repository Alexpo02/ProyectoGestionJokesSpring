import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { JokeService } from '../../services/joke.service';
import { Joke } from '../../models/joke.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-joke-list',
  templateUrl: './joke-list.component.html',
  styleUrls: ['./joke-list.component.css'],
})
export class JokeListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'category', 'language', 'type', 'text1', 'text2', 'acciones'];
  dataSource = new MatTableDataSource<Joke>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  filtro: string = '';

  constructor(private jokeService: JokeService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.cargarJokes();
  }

  cargarJokes(): void {
    this.jokeService.getJokes().subscribe((jokes) => {
      this.dataSource.data = jokes;
      this.dataSource.paginator = this.paginator;
    });
  }

  eliminarJoke(id: number): void {
    if (confirm('¿Está seguro de que desea eliminar esta joke?')) {
      this.jokeService.deleteJoke(id).subscribe(() => {
        this.snackBar.open('Joke eliminada con éxito', 'Cerrar', { duration: 3000 });
        this.cargarJokes();
      });
    }
  }

  aplicarFiltro(): void {
    this.dataSource.filter = this.filtro.trim().toLowerCase();
  }
}
