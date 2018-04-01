import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { LecturaMedidor } from './lectura-medidor.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<LecturaMedidor>;

@Injectable()
export class LecturaMedidorService {

    private resourceUrl =  SERVER_API_URL + 'api/lectura-medidors';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/lectura-medidors';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(lecturaMedidor: LecturaMedidor): Observable<EntityResponseType> {
        const copy = this.convert(lecturaMedidor);
        return this.http.post<LecturaMedidor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(lecturaMedidor: LecturaMedidor): Observable<EntityResponseType> {
        const copy = this.convert(lecturaMedidor);
        return this.http.put<LecturaMedidor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LecturaMedidor>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LecturaMedidor[]>> {
        const options = createRequestOption(req);
        return this.http.get<LecturaMedidor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LecturaMedidor[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<LecturaMedidor[]>> {
        const options = createRequestOption(req);
        return this.http.get<LecturaMedidor[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LecturaMedidor[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LecturaMedidor = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LecturaMedidor[]>): HttpResponse<LecturaMedidor[]> {
        const jsonResponse: LecturaMedidor[] = res.body;
        const body: LecturaMedidor[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LecturaMedidor.
     */
    private convertItemFromServer(lecturaMedidor: LecturaMedidor): LecturaMedidor {
        const copy: LecturaMedidor = Object.assign({}, lecturaMedidor);
        copy.fecha = this.dateUtils
            .convertDateTimeFromServer(lecturaMedidor.fecha);
        return copy;
    }

    /**
     * Convert a LecturaMedidor to a JSON which can be sent to the server.
     */
    private convert(lecturaMedidor: LecturaMedidor): LecturaMedidor {
        const copy: LecturaMedidor = Object.assign({}, lecturaMedidor);

        copy.fecha = this.dateUtils.toDate(lecturaMedidor.fecha);
        return copy;
    }
}
