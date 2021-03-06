import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Photo } from './photo.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PhotoService {

    private resourceUrl = 'api/photos';

    constructor(private http: Http) { }

    create(photo: Photo): Observable<Photo> {
        const copy = this.convert(photo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(photo: Photo): Observable<Photo> {
        const copy = this.convert(photo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: string): Observable<Photo> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(photo: Photo): Photo {
        const copy: Photo = Object.assign({}, photo);
        return copy;
    }
}
