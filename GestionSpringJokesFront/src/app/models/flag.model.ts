import { Joke } from './joke.model';

export interface Flag {
  id: number;
  flag: string;
  jokeses?: Joke[];
}
