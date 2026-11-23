export interface LibroRequest {
  isbn: number,
  titolo: string,
  genere: string,
  data: string,
  prezzo: number
  autoriIDs: number[]
}
