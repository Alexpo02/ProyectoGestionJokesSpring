import { Joke } from "./joke.model";

export interface Category {
    id: number;
    category: string;
    jokes: Joke[];
  }
  