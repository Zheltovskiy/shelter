/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ShelterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PetDetailComponent } from '../../../../../../main/webapp/app/entities/pet/pet-detail.component';
import { PetService } from '../../../../../../main/webapp/app/entities/pet/pet.service';
import { Pet } from '../../../../../../main/webapp/app/entities/pet/pet.model';

describe('Component Tests', () => {

    describe('Pet Management Detail Component', () => {
        let comp: PetDetailComponent;
        let fixture: ComponentFixture<PetDetailComponent>;
        let service: PetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ShelterTestModule],
                declarations: [PetDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PetService,
                    JhiEventManager
                ]
            }).overrideTemplate(PetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Pet('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pet).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
