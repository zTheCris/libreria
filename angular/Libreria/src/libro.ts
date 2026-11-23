import { Autore } from "./autore";
import { Genere } from "./genere";

export interface Libro {
  isbn: number;
  titolo:string;
  anno:string;
  genere: Genere | null;
  prezzo: number;
  autori: Autore[] | undefined;
}
