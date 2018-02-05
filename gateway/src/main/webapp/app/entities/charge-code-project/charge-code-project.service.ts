import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ChargeCodeProject } from './charge-code-project.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ChargeCodeProject>;

@Injectable()
export class ChargeCodeProjectService {

    private resourceUrl =  SERVER_API_URL + 'chargecodes/api/charge-code-projects';

    constructor(private http: HttpClient) { }

    create(chargeCodeProject: ChargeCodeProject): Observable<EntityResponseType> {
        const copy = this.convert(chargeCodeProject);
        return this.http.post<ChargeCodeProject>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(chargeCodeProject: ChargeCodeProject): Observable<EntityResponseType> {
        const copy = this.convert(chargeCodeProject);
        return this.http.put<ChargeCodeProject>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ChargeCodeProject>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ChargeCodeProject[]>> {
        const options = createRequestOption(req);
        return this.http.get<ChargeCodeProject[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ChargeCodeProject[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ChargeCodeProject = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ChargeCodeProject[]>): HttpResponse<ChargeCodeProject[]> {
        const jsonResponse: ChargeCodeProject[] = res.body;
        const body: ChargeCodeProject[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ChargeCodeProject.
     */
    private convertItemFromServer(chargeCodeProject: ChargeCodeProject): ChargeCodeProject {
        const copy: ChargeCodeProject = Object.assign({}, chargeCodeProject);
        return copy;
    }

    /**
     * Convert a ChargeCodeProject to a JSON which can be sent to the server.
     */
    private convert(chargeCodeProject: ChargeCodeProject): ChargeCodeProject {
        const copy: ChargeCodeProject = Object.assign({}, chargeCodeProject);
        return copy;
    }
}
