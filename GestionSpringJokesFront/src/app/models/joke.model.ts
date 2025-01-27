import { Language } from './language.model';
import { Flag } from './flag.model';
import { Category } from './category.model';
import { Type } from './type.model';

export interface Joke {
  id: number;
  categories?: Category;
  language?: Language; 
  types?: Type;
  text1: string;
  text2: string;
  flagses: Flag[];
}
