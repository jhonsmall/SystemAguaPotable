import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Clasificacion } from './clasificacion.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Clasificacion>;

@Injectable()
export class ClasificacionService {

    private resourceUrl =  SERVER_API_URL + 'api/clasificacions';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/clasificacions';

    constructor(private http: HttpClient) { }

    create(clasificacion: Clasificacion): Observable<EntityResponseType> {
        const copy = this.convert(clasificacion);
        return this.http.post<Clasificacion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(clasificacion: Clasificacion): Observable<EntityResponseType> {
        const copy = this.convert(clasificacion);
        return this.http.put<Clasificacion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Clasificacion>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Clasificacion[]>> {
        const options = createRequestOption(req);
        return this.http.get<Clasificacion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Clasificacion[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Clasificacion[]>> {
        const options = createRequestOption(req);
        return this.http.get<Clasificacion[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Clasificacion[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Clasificacion = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Clasificacion[]>): HttpResponse<Clasificacion[]> {
        const jsonResponse: Clasificacion[] = res.body;
        const body: Clasificacion[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Clasificacion.
     */
    private convertItemFromServer(clasificacion: Clasificacion): Clasificacion {
        const copy: Clasificacion = Object.assign({}, clasificacion);
        return copy;
    }

    /**
     * Convert a Clasificacion to a JSON which can be sent to the server.
     */
    private convert(clasificacion: Clasificacion): Clasificacion {
        const copy: Clasificacion = Object.assign({}, clasificacion);
        return copy;
    }
}
