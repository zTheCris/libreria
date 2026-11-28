import { CasaEditrice } from './casa-editrice';
import { Libro } from "./libro";

export interface Copia {
 idCopia: number;
  libro: Libro;
  casaEditrice: CasaEditrice;
  numeroCopie: number;
}
