import { Nazionalita } from './nazionalita';
export interface Autore {
  idAutore: number;
  nome: string;
  cognome: string;
  data: string;
  nazionalita: Nazionalita;
}
