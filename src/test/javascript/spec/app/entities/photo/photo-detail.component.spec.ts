/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ShelterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PhotoDetailComponent } from '../../../../../../main/webapp/app/entities/photo/photo-detail.component';
import { PhotoService } from '../../../../../../main/webapp/app/entities/photo/photo.service';
import { Photo } from '../../../../../../main/webapp/app/entities/photo/photo.model';

describe('Component Tests', () => {

    describe('Photo Management Detail Component', () => {
        let comp: PhotoDetailComponent;
        let fixture: ComponentFixture<PhotoDetailComponent>;
        let service: PhotoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ShelterTestModule],
                declarations: [PhotoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PhotoService,
                    JhiEventManager
                ]
            }).overrideTemplate(PhotoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PhotoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhotoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Photo('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.photo).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
