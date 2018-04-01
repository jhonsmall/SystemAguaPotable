import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CostoMedidor } from './costo-medidor.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CostoMedidor>;

@Injectable()
export class CostoMedidorService {

    private resourceUrl =  SERVER_API_URL + 'api/costo-medidors';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/costo-medidors';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(costoMedidor: CostoMedidor): Observable<EntityResponseType> {
        const copy = this.convert(costoMedidor);
        return this.http.post<CostoMedidor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(costoMedidor: CostoMedidor): Observable<EntityResponseType> {
        const copy = this.convert(costoMedidor);
        return this.http.put<CostoMedidor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CostoMedidor>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CostoMedidor[]>> {
        const options = createRequestOption(req);
        return this.http.get<CostoMedidor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CostoMedidor[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<CostoMedidor[]>> {
        const options = createRequestOption(req);
        return this.http.get<CostoMedidor[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CostoMedidor[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CostoMedidor = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CostoMedidor[]>): HttpResponse<CostoMedidor[]> {
        const jsonResponse: CostoMedidor[] = res.body;
        const body: CostoMedidor[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CostoMedidor.
     */
    private convertItemFromServer(costoMedidor: CostoMedidor): CostoMedidor {
        const copy: CostoMedidor = Object.assign({}, costoMedidor);
        copy.fecha = this.dateUtils
            .convertDateTimeFromServer(costoMedidor.fecha);
        return copy;
    }

    /**
     * Convert a CostoMedidor to a JSON which can be sent to the server.
     */
    private convert(costoMedidor: CostoMedidor): CostoMedidor {
        const copy: CostoMedidor = Object.assign({}, costoMedidor);

        copy.fecha = this.dateUtils.toDate(costoMedidor.fecha);
        return copy;
    }
}
