import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Servicio } from './servicio.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Servicio>;

@Injectable()
export class ServicioService {

    private resourceUrl =  SERVER_API_URL + 'api/servicios';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/servicios';

    constructor(private http: HttpClient) { }

    create(servicio: Servicio): Observable<EntityResponseType> {
        const copy = this.convert(servicio);
        return this.http.post<Servicio>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(servicio: Servicio): Observable<EntityResponseType> {
        const copy = this.convert(servicio);
        return this.http.put<Servicio>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Servicio>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Servicio[]>> {
        const options = createRequestOption(req);
        return this.http.get<Servicio[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Servicio[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Servicio[]>> {
        const options = createRequestOption(req);
        return this.http.get<Servicio[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Servicio[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Servicio = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Servicio[]>): HttpResponse<Servicio[]> {
        const jsonResponse: Servicio[] = res.body;
        const body: Servicio[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Servicio.
     */
    private convertItemFromServer(servicio: Servicio): Servicio {
        const copy: Servicio = Object.assign({}, servicio);
        return copy;
    }

    /**
     * Convert a Servicio to a JSON which can be sent to the server.
     */
    private convert(servicio: Servicio): Servicio {
        const copy: Servicio = Object.assign({}, servicio);
        return copy;
    }
}
