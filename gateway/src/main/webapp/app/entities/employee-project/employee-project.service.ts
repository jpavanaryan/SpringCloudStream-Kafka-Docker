import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { EmployeeProject } from './employee-project.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EmployeeProject>;

@Injectable()
export class EmployeeProjectService {

    private resourceUrl =  SERVER_API_URL + 'projects/api/employee-projects';

    constructor(private http: HttpClient) { }

    create(employeeProject: EmployeeProject): Observable<EntityResponseType> {
        const copy = this.convert(employeeProject);
        return this.http.post<EmployeeProject>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(employeeProject: EmployeeProject): Observable<EntityResponseType> {
        const copy = this.convert(employeeProject);
        return this.http.put<EmployeeProject>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EmployeeProject>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EmployeeProject[]>> {
        const options = createRequestOption(req);
        return this.http.get<EmployeeProject[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EmployeeProject[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EmployeeProject = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EmployeeProject[]>): HttpResponse<EmployeeProject[]> {
        const jsonResponse: EmployeeProject[] = res.body;
        const body: EmployeeProject[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EmployeeProject.
     */
    private convertItemFromServer(employeeProject: EmployeeProject): EmployeeProject {
        const copy: EmployeeProject = Object.assign({}, employeeProject);
        return copy;
    }

    /**
     * Convert a EmployeeProject to a JSON which can be sent to the server.
     */
    private convert(employeeProject: EmployeeProject): EmployeeProject {
        const copy: EmployeeProject = Object.assign({}, employeeProject);
        return copy;
    }
}
