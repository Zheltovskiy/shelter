/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ShelterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ShelterDetailComponent } from '../../../../../../main/webapp/app/entities/shelter/shelter-detail.component';
import { ShelterService } from '../../../../../../main/webapp/app/entities/shelter/shelter.service';
import { Shelter } from '../../../../../../main/webapp/app/entities/shelter/shelter.model';

describe('Component Tests', () => {

    describe('Shelter Management Detail Component', () => {
        let comp: ShelterDetailComponent;
        let fixture: ComponentFixture<ShelterDetailComponent>;
        let service: ShelterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ShelterTestModule],
                declarations: [ShelterDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ShelterService,
                    JhiEventManager
                ]
            }).overrideTemplate(ShelterDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShelterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShelterService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Shelter('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.shelter).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
