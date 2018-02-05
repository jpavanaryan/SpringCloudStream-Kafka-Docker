import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ChargeCode } from './charge-code.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ChargeCode>;

@Injectable()
export class ChargeCodeService {

    private resourceUrl =  SERVER_API_URL + 'chargecodes/api/charge-codes';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(chargeCode: ChargeCode): Observable<EntityResponseType> {
        const copy = this.convert(chargeCode);
        return this.http.post<ChargeCode>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(chargeCode: ChargeCode): Observable<EntityResponseType> {
        const copy = this.convert(chargeCode);
        return this.http.put<ChargeCode>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ChargeCode>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ChargeCode[]>> {
        const options = createRequestOption(req);
        return this.http.get<ChargeCode[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ChargeCode[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ChargeCode = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ChargeCode[]>): HttpResponse<ChargeCode[]> {
        const jsonResponse: ChargeCode[] = res.body;
        const body: ChargeCode[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ChargeCode.
     */
    private convertItemFromServer(chargeCode: ChargeCode): ChargeCode {
        const copy: ChargeCode = Object.assign({}, chargeCode);
        copy.chargeCodeStartDate = this.dateUtils
            .convertLocalDateFromServer(chargeCode.chargeCodeStartDate);
        copy.chargeCodeEndDate = this.dateUtils
            .convertLocalDateFromServer(chargeCode.chargeCodeEndDate);
        return copy;
    }

    /**
     * Convert a ChargeCode to a JSON which can be sent to the server.
     */
    private convert(chargeCode: ChargeCode): ChargeCode {
        const copy: ChargeCode = Object.assign({}, chargeCode);
        copy.chargeCodeStartDate = this.dateUtils
            .convertLocalDateToServer(chargeCode.chargeCodeStartDate);
        copy.chargeCodeEndDate = this.dateUtils
            .convertLocalDateToServer(chargeCode.chargeCodeEndDate);
        return copy;
    }
}
