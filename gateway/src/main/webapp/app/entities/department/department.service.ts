import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Department } from './department.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Department>;

@Injectable()
export class DepartmentService {

    private resourceUrl =  SERVER_API_URL + 'projects/api/departments';

    constructor(private http: HttpClient) { }

    create(department: Department): Observable<EntityResponseType> {
        const copy = this.convert(department);
        return this.http.post<Department>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(department: Department): Observable<EntityResponseType> {
        const copy = this.convert(department);
        return this.http.put<Department>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Department>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Department[]>> {
        const options = createRequestOption(req);
        return this.http.get<Department[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Department[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Department = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Department[]>): HttpResponse<Department[]> {
        const jsonResponse: Department[] = res.body;
        const body: Department[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Department.
     */
    private convertItemFromServer(department: Department): Department {
        const copy: Department = Object.assign({}, department);
        return copy;
    }

    /**
     * Convert a Department to a JSON which can be sent to the server.
     */
    private convert(department: Department): Department {
        const copy: Department = Object.assign({}, department);
        return copy;
    }
}
