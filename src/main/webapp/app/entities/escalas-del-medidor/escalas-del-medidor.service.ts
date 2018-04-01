import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { EscalasDelMedidor } from './escalas-del-medidor.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EscalasDelMedidor>;

@Injectable()
export class EscalasDelMedidorService {

    private resourceUrl =  SERVER_API_URL + 'api/escalas-del-medidors';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/escalas-del-medidors';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(escalasDelMedidor: EscalasDelMedidor): Observable<EntityResponseType> {
        const copy = this.convert(escalasDelMedidor);
        return this.http.post<EscalasDelMedidor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(escalasDelMedidor: EscalasDelMedidor): Observable<EntityResponseType> {
        const copy = this.convert(escalasDelMedidor);
        return this.http.put<EscalasDelMedidor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EscalasDelMedidor>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EscalasDelMedidor[]>> {
        const options = createRequestOption(req);
        return this.http.get<EscalasDelMedidor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EscalasDelMedidor[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<EscalasDelMedidor[]>> {
        const options = createRequestOption(req);
        return this.http.get<EscalasDelMedidor[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EscalasDelMedidor[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EscalasDelMedidor = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EscalasDelMedidor[]>): HttpResponse<EscalasDelMedidor[]> {
        const jsonResponse: EscalasDelMedidor[] = res.body;
        const body: EscalasDelMedidor[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EscalasDelMedidor.
     */
    private convertItemFromServer(escalasDelMedidor: EscalasDelMedidor): EscalasDelMedidor {
        const copy: EscalasDelMedidor = Object.assign({}, escalasDelMedidor);
        copy.fecha = this.dateUtils
            .convertDateTimeFromServer(escalasDelMedidor.fecha);
        return copy;
    }

    /**
     * Convert a EscalasDelMedidor to a JSON which can be sent to the server.
     */
    private convert(escalasDelMedidor: EscalasDelMedidor): EscalasDelMedidor {
        const copy: EscalasDelMedidor = Object.assign({}, escalasDelMedidor);

        copy.fecha = this.dateUtils.toDate(escalasDelMedidor.fecha);
        return copy;
    }
}
