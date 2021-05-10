export type UsersResponse ={
    content: User[];
    totalPages: number;
}

export type Role ={
    id:number;
    authority:string;
}

export type User={
    id: number;
    name: string;
    email: string;
    roles: Role[];
}