// DAO
export interface Book {
  id: number;
  title: string;
  author: string;
  pages: number;
  finished: boolean;
}

// DTO
export interface CreateBookRequest {
  title: string;
  author: string;
  pages: number;
}