import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Costo } from './costo.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Costo>;

@Injectable()
export class CostoService {

    private resourceUrl =  SERVER_API_URL + 'api/costos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/costos';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(costo: Costo): Observable<EntityResponseType> {
        const copy = this.convert(costo);
        return this.http.post<Costo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(costo: Costo): Observable<EntityResponseType> {
        const copy = this.convert(costo);
        return this.http.put<Costo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Costo>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Costo[]>> {
        const options = createRequestOption(req);
        return this.http.get<Costo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Costo[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Costo[]>> {
        const options = createRequestOption(req);
        return this.http.get<Costo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Costo[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Costo = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Costo[]>): HttpResponse<Costo[]> {
        const jsonResponse: Costo[] = res.body;
        const body: Costo[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Costo.
     */
    private convertItemFromServer(costo: Costo): Costo {
        const copy: Costo = Object.assign({}, costo);
        copy.fecha = this.dateUtils
            .convertDateTimeFromServer(costo.fecha);
        return copy;
    }

    /**
     * Convert a Costo to a JSON which can be sent to the server.
     */
    private convert(costo: Costo): Costo {
        const copy: Costo = Object.assign({}, costo);

        copy.fecha = this.dateUtils.toDate(costo.fecha);
        return copy;
    }
}
