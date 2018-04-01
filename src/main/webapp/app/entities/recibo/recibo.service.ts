import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Recibo } from './recibo.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Recibo>;

@Injectable()
export class ReciboService {

    private resourceUrl =  SERVER_API_URL + 'api/recibos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/recibos';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(recibo: Recibo): Observable<EntityResponseType> {
        const copy = this.convert(recibo);
        return this.http.post<Recibo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(recibo: Recibo): Observable<EntityResponseType> {
        const copy = this.convert(recibo);
        return this.http.put<Recibo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Recibo>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Recibo[]>> {
        const options = createRequestOption(req);
        return this.http.get<Recibo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Recibo[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Recibo[]>> {
        const options = createRequestOption(req);
        return this.http.get<Recibo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Recibo[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Recibo = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Recibo[]>): HttpResponse<Recibo[]> {
        const jsonResponse: Recibo[] = res.body;
        const body: Recibo[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Recibo.
     */
    private convertItemFromServer(recibo: Recibo): Recibo {
        const copy: Recibo = Object.assign({}, recibo);
        copy.fechagenera = this.dateUtils
            .convertDateTimeFromServer(recibo.fechagenera);
        copy.fechapaga = this.dateUtils
            .convertDateTimeFromServer(recibo.fechapaga);
        return copy;
    }

    /**
     * Convert a Recibo to a JSON which can be sent to the server.
     */
    private convert(recibo: Recibo): Recibo {
        const copy: Recibo = Object.assign({}, recibo);

        copy.fechagenera = this.dateUtils.toDate(recibo.fechagenera);

        copy.fechapaga = this.dateUtils.toDate(recibo.fechapaga);
        return copy;
    }
}
